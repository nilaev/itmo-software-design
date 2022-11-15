const constant = require('./resources/store.js')

const axios = require('axios')

const request = async (url) => {
  url = decodeURI(url);
  url = encodeURI(url);
  const response = await axios.get(url);
  if (response.status === 200) {
    return await response.data
  } else {
    throw Error('Ошибка HTTP: ' + response.status)
  }
}

const getApiNewsFeedInfo = async (hashtag, startTime, endTime) => {
  const mainUrlPath = `${constant.api.VK_API_PATH}/newsfeed.search?access_token=${constant.api.SERVICE_ACCESS_KEY}&v=${constant.api.VK_API_VERSION}`
  const url = `${mainUrlPath}&q=${hashtag}&start_time=${startTime}&end_time=${endTime}`
  return await request(url)
}

module.exports = getApiNewsFeedInfo