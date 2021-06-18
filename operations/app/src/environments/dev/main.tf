locals {
  target_env = "env"
}

terraform {
  required_version = "= 0.14.5" # This version must also be changed in other environments
  required_providers {
    azurerm = {
      source = "hashicorp/azurerm"
      version = ">= 2.61.0" # This version must also be changed in other environments
    }
  }
  backend "azurerm" {
    resource_group_name = "waters"
    storage_account_name = "watersdata"
    container_name = "terraformstate"
    key = "terraform.tfstate"
  }
}

provider "azurerm" {
  features {}
  skip_provider_registration = true
  subscription_id = "72697d21-ec57-47c8-8998-1924b0ce0784"
}

module "prime_data_hub" {
  source = "../../modules/prime_data_hub"
  environment = local.target_env
  resource_group = "prime-data-hub-${local.target_env}"
  resource_prefix = "pdhdev"
  #okta_redirect_url = "https://prime.cdc.gov/download"
  https_cert_names = ["prime-cdc-gov", "reportstream-cdc-gov"]
  rsa_key_2048 = "pdhprod-2048-key"
  rsa_key_4096 = "pdhprod-4096-key"
  is_metabase_env = true
}
