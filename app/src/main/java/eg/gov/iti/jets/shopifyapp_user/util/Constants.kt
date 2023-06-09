package eg.gov.iti.jets.shopifyapp_user.util

import eg.gov.iti.jets.shopifyapp_user.BuildConfig

const val BASE_URL = "https://${BuildConfig.api_key}:${BuildConfig.api_password}@${BuildConfig.store_name}.myshopify.com/admin/api/${BuildConfig.api_version}/"

