import tweepy, time, sys
from tweepy.streaming import StreamListener
from tweepy import OAuthHandler
from tweepy import Stream
import requests
import json
url = 'http://ppdb.gustafn.com/pp'
 
CONSUMER_KEY = 'M6gCybQsVWx92siwhrupVzwRN'
CONSUMER_SECRET = '11AQJVDi8MCmgtWxuxhPXNlDxfng8scETuqL1N8c5dNndGXP1a'
ACCESS_KEY = '827411483487133697-t4No89IgY4RLLhroEgslBZ343qEiqiO'
ACCESS_SECRET = 'qtZjuSZ9l58nCXOD2RpWUbAhG1SsWme14q59CEWD9Vi8u'
auth = tweepy.OAuthHandler(CONSUMER_KEY, CONSUMER_SECRET)
auth.set_access_token(ACCESS_KEY, ACCESS_SECRET)

api = tweepy.API(auth)

class StdOutListener(StreamListener):

    def on_data(self, data):
        data = json.loads(data)
        print data['text']
        target = ""
        for mention in data['entities']['user_mentions']:
        	if target == "" and mention['screen_name'] != "ppdatabase":
        		target = mention['screen_name']
        		break
        response = requests.get(url)
        api.update_status("@" + target + " " + response.json()['word'])
        return True

    def on_error(self, status):
        print status


if __name__ == '__main__':

    #This handles Twitter authetification and the connection to Twitter Streaming API
    l = StdOutListener()
    stream = Stream(auth, l)

    #This line filter Twitter Streams to capture data by the keywords: 'python', 'javascript', 'ruby'
    stream.filter(track=['@ppdatabase'])