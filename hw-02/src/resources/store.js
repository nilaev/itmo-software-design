// SERVICE_ACCESS_KEY можно зарегистрировать тут: https://dev.vk.com/mini-apps/management/settings
// Подробнее о методе vk api тут: https://dev.vk.com/method/newsfeed.search
const SERVICE_ACCESS_KEY = 'тут должен быть ваш персональный ключ'
const VK_API_PATH = 'https://api.vk.com/method'
const VK_API_VERSION = '5.131'

const SECONDS_IN_HOUR = 3600

module.exports = {
  api: {
    VK_API_PATH,
    VK_API_VERSION,
    SERVICE_ACCESS_KEY
  },
  SECONDS_IN_HOUR
}