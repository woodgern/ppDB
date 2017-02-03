import tweepy, time, sys
 
CONSUMER_KEY = 'M6gCybQsVWx92siwhrupVzwRN'
CONSUMER_SECRET = '11AQJVDi8MCmgtWxuxhPXNlDxfng8scETuqL1N8c5dNndGXP1a'
ACCESS_KEY = '827411483487133697-t4No89IgY4RLLhroEgslBZ343qEiqiO'
ACCESS_SECRET = 'qtZjuSZ9l58nCXOD2RpWUbAhG1SsWme14q59CEWD9Vi8u'
auth = tweepy.OAuthHandler(CONSUMER_KEY, CONSUMER_SECRET)
auth.set_access_token(ACCESS_KEY, ACCESS_SECRET)
api = tweepy.API(auth)

mentions = api.mentions_timeline()

for mention in mentions:
    print mention.text
    print mention.user.screen_name