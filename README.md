# ppDB

A micro service for random synonyms of vulgar words.

##Usage
Disclaimer; this application contains vulgar words. do not use if that is a problem. 

Reply form endpoints is formated as Json 
```JSON
{
    _id: Integer,
    word: String
}
```

##Endpoints 

###/pp
```http
curl [URL]/pp
```
Returns random synonym of penis 