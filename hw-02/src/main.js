const constant = require('./resources/store.js')
const getApiNewsFeedInfo = require('./api.js')

const tweetCounter = async (hashtag, hoursNumber) => {
  if (typeof hashtag !== 'string' || typeof hoursNumber !== 'number') {
    throw Error('Неправильный формат ввода')
  }
  const currentTime = parseInt(new Date().getTime() / 1000)
  const startTime = currentTime - constant.SECONDS_IN_HOUR * hoursNumber
  const ans = []
  for (let time = startTime; time < currentTime; time += constant.SECONDS_IN_HOUR) {
    const postInHour = await getApiNewsFeedInfo(hashtag, time, time + constant.SECONDS_IN_HOUR);
    ans.push(postInHour.response.total_count);
  }
  return ans
}

module.exports = tweetCounter