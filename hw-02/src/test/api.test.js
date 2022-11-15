const getApiNewsFeedInfo = require('../api.js')


describe('getApiNewsFeedInfo', () => {
  it('Код ошибки при неправильном формате ввода', async () => {
    const result = await getApiNewsFeedInfo('текст', 'ошибочный', 'ввод');
    expect(result.error['error_code']).toBe(100);
  })
  
  it('Обращение к несуществующему урлу', () => {
    expect(async () => {
      await expect(getApiNewsFeedInfo('ошибочный-url.ru'))
        .rejects.toThrow()
    })
  })
})