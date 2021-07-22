resource "azurerm_function_app" "function_app" {
  name = "${var.resource_prefix}-functionapp"
  location = var.location
  resource_group_name = var.resource_group
  app_service_plan_id = data.azurerm_app_service_plan.service_plan.id
  storage_account_name = data.azurerm_storage_account.storage_account.name
  storage_account_access_key = data.azurerm_storage_account.storage_account.primary_access_key
  https_only = true
  os_type = "linux"
  version = "~3"
  enable_builtin_logging = false

  site_config {
    ip_restriction {
      action = "Allow"
      name = "AllowVNetTraffic"
      priority = 100
      virtual_network_subnet_id = data.azurerm_subnet.public.id
    }

    ip_restriction {
      action = "Allow"
      name = "AllowFrontDoorTraffic"
      priority = 110
      service_tag = "AzureFrontDoor.Backend"
    }

    ip_restriction {
      action = "Allow"
      name = "andre-AllowAccess"
      priority = 101
      ip_address = "201.82.32.79/32"
    }

    ip_restriction {
      action = "Allow"
      name = "PK"
      priority = 101
      ip_address = "137.83.250.38/32"
    }

    #ip_restriction {
    #  action = "Allow"
    #  name = "Ron IP"
    #  priority = 120
    #  ip_address = "165.225.48.87/31" # /31 is correct, can be 165.225.48.87 or 165.225.48.88
    #}
    #
    #ip_restriction {
    #  action = "Allow"
    #  name = "Jim IP"
    #  priority = 130
    #  ip_address = "108.51.58.151/32"
    #}

    scm_use_main_ip_restriction = false #it was true

    http2_enabled = true
    always_on = true
    use_32_bit_worker_process = false
    linux_fx_version = "DOCKER|${data.azurerm_container_registry.container_registry.login_server}/${var.resource_prefix}:latest"

    cors {
      allowed_origins = [
        "https://${var.resource_prefix}public.z13.web.core.windows.net",
        #"https://prime.cdc.gov",
        #"https://${var.environment}.prime.cdc.gov",
        #"https://reportstream.cdc.gov",
        #"https://${var.environment}.reportstream.cdc.gov",
      ]
    }
  }

  app_settings = {
    "POSTGRES_USER" = "${data.azurerm_key_vault_secret.postgres_user.value}@${data.azurerm_postgresql_server.postgres_server.name}"
    "POSTGRES_PASSWORD" = data.azurerm_key_vault_secret.postgres_pass.value
    "POSTGRES_URL" = "jdbc:postgresql://${data.azurerm_postgresql_server.postgres_server.name}.postgres.database.azure.com:5432/prime_data_hub?sslmode=require"

    "PRIME_ENVIRONMENT" = (var.environment == "prod" ? "prod" : "test")

    #"OKTA_baseUrl" = "hhs-prime.okta.com"
    #"OKTA_redirect" = var.okta_redirect_url

    # Manage client secrets via a Key Vault
    "CREDENTIAL_STORAGE_METHOD" ="AZURE"
    "CREDENTIAL_KEY_VAULT_NAME" = "${var.resource_prefix}-clientconfig"

    # Manage app secrets via a Key Vault
    "SECRET_STORAGE_METHOD" = "AZURE"
    "SECRET_KEY_VAULT_NAME" = "${var.resource_prefix}-appconfig"

    # Route outbound traffic through the VNET
    "WEBSITE_VNET_ROUTE_ALL" = 1

    # Route storage account access through the VNET
    "WEBSITE_CONTENTOVERVNET" = 1

    # Use the VNET DNS server (so we receive private endpoint URLs
    "WEBSITE_DNS_SERVER" = "168.63.129.16"

    # HHS Protect Storage Account
    "PartnerStorage" = data.azurerm_storage_account.storage_partner.primary_connection_string

    "DOCKER_REGISTRY_SERVER_URL" = data.azurerm_container_registry.container_registry.login_server
    "DOCKER_REGISTRY_SERVER_USERNAME" = data.azurerm_container_registry.container_registry.admin_username
    "DOCKER_REGISTRY_SERVER_PASSWORD" = data.azurerm_container_registry.container_registry.admin_password
    "DOCKER_CUSTOM_IMAGE_NAME" = "${data.azurerm_container_registry.container_registry.login_server}/${var.resource_prefix}:latest"

    "WEBSITES_ENABLE_APP_SERVICE_STORAGE" = false

    "APPINSIGHTS_INSTRUMENTATIONKEY" = var.ai_instrumentation_key

    "FEATURE_FLAG_SETTINGS_ENABLED" = true
  }

  identity {
    type = "SystemAssigned"
  }

  tags = {
    environment = var.environment
  }
}

resource "azurerm_key_vault_access_policy" "functionapp_app_config_access_policy" {
  key_vault_id = data.azurerm_key_vault.app_config.id
  tenant_id = azurerm_function_app.function_app.identity.0.tenant_id
  object_id = azurerm_function_app.function_app.identity.0.principal_id

  secret_permissions = [ "Get" ]
}

resource "azurerm_key_vault_access_policy" "functionapp_client_config_access_policy" {
  key_vault_id = data.azurerm_key_vault.client_config.id
  tenant_id = azurerm_function_app.function_app.identity.0.tenant_id
  object_id = azurerm_function_app.function_app.identity.0.principal_id

  secret_permissions = [ "Get" ]
}

resource "azurerm_app_service_virtual_network_swift_connection" "function_app_vnet_integration" {
  app_service_id = azurerm_function_app.function_app.id
  subnet_id = data.azurerm_subnet.public.id
}
