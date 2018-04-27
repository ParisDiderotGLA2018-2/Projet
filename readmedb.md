# Database Tutorial

The general mappings are availaible in persitance, in the json file named
`table.json.`

## 1. Mapping with curl

- Example of mapping with curl.

```curl
curl -XPUT 'http://localhost:9200/user/' -H 'Content-Type: application/json' -d '{
    "mappings" : {
        "user" : {
            "properties" : {
                "username" : { "type" : "text" },
                "password" : {"type" : "password"}
            }
        }
      }
}'
```

## 2. Mapping without curl.

1. Start elaticsearch.
2. Start kibana.
3. Go in kibana to the dev tool onglet
4. Put the code below to create for example an index for location
```json
PUT location
{
    "mappings":{
      "location" :
      {
            "properties" :
            {
                "idMap" : {"type" : "text"},
                "place" : {"type" : "text"},
                "lat" : {"type" : "text"},
                "lng" : {"type" : "text"},
                "tag" : {"type" : "text"},
                "message" : {"type" : "text"},
                "filename"  : {"type" : "text"}
            }
    }
    }
}
```
