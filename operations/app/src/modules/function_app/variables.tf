variable "environment" {
    type = string
    description = "Target Environment"
}

variable "resource_group" {
    type = string
    description = "Resource Group Name"
}

variable "resource_prefix" {
    type = string
    description = "Resource Prefix"
}

variable "location" {
    type = string
    description = "Function App Location"
}

variable "ai_instrumentation_key" {
    type = string
    description = "Application Insights Instrumentation Key"
    sensitive = true
}

<<<<<<< HEAD
#variable "okta_redirect_url" {
#    type = string
#    description = "Okta Redirect URL"
#}

variable "app_config_key_vault_id" {
    type = string
    description = "Key Vault used for function app configuration"
}

variable "client_config_key_vault_id" {
    type = string
    description = "Key Vault used for client credential secrets"
}

variable "storage_partner_connection_string" {
    type = string
    description = "Storage account to export data with HHS Protect"
=======
variable "okta_redirect_url" {
    type = string
    description = "Okta Redirect URL"
>>>>>>> origin/master
}