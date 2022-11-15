const axios = require('axios')
const tweetCounter = require('../main.js')

jest.mock('axios');

const returnValue = {
  response: {
    count: 11,
    items: [],
    total_count: 11,
  }
}

describe('tweetCounter', () => {
  
  it('Ошибка при неправильном формате ввода', () => {
    expect(async () => {
      await expect(await tweetCounter(2, 'три'))
        .rejects.toThrow('Неправильный формат ввода')
    })
  })
  
  it('Mock vk api', async () => {
    axios.get = jest.fn();
    axios.get.mockResolvedValueOnce({data: {
        response: {
          count: 120,
          items: [],
          total_count: 120,
        }
      }, status: 200})
    axios.get.mockResolvedValue({data: returnValue, status: 200});
  
    const ans = await tweetCounter('кот', 5)
    expect(ans).toStrictEqual([120, 11, 11, 11, 11])
  })
})