# A6
A6 group

# End points 

## Create notice 
POST
/notices/{id}
request:
{
   :id     "ldskjdnjksdn"
   :title  "title of my first event notice"
   :description "description of my first event notice"
}


## Show notice 
GET
/notices/{id}

request:
{
   :id     "ldskjdnjksdn"
}

response:
{
   :code 200
   :id     "ldskjdnjksdn"
   :title  "title of my first event notice"
   :description "description of my first event notice"
}


## Update notice 
POST
/notices/{id}
request:
{
   :id     "ldskjdnjksdn"
   :title  "new title of my first event notice"
   :description "new description of my first event notice"
}

response: { :code 200 :id "ldskjdnjksdn" }

## Get all notices 
GET
/notices/
request:
[ 
  { :id "ldskjdnjksdn" :title "title01" :description "description01 of notice" },
  { :id "gvdvdsbhnknk" :title "title02" :description "description02 of notice" }
]

