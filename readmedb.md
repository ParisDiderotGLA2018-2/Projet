#TUTO

Les mapping général sont disponible dans persistance dans le fichier json table.json
##1. Mapping avec curl

- Exemple de mapping avec curl.

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

##2. Faire un mapping sans curl.

1. Démarrer elaticsearch.
2. Démarrer kibana.
3. Aller sur kibana à l'onget dev tool
4. Mettre le code ci dessous pour créer par exemple index location
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
