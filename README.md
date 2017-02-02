# Party-Tonight-Backend

SignUp
======

Maker
====

uri: /partymaker/maker/signup

method: POST

type: application/json

body: 
{

	"userName":"John",
	"phoneNumber":"066565",
	"email":"email.com",
	"emergencyContact":"contact",
	"password":123,
	"billing":{
		"card_number":"1234"
	}
}

Dancer
=======


uri: /partymaker/dancer/signup

method: POST

type: application/json

body: 
{

	"userName":"M",
	"phoneNumber":"066565",
	"email":"m.com",
	"emergencyContact":"contact",
	"password":123
}


SignIn:
======


uri: /signin

method: GET

type: application/json

header: Authorization = Basic bS5jb206MTIz


Create event
=============


uri: /partymaker/maker/event/create

method: POST

type: application/json

header: x-auth-token = signInMakerToken

body:

{

    "club_name": "NewEvent",
    "date": "cannot be null",
    "location": "cannot be null",
    "club_capacity": "cannot be null",
    "party_name": "event",
    "zip_code": "231",
    
    "bottles": [
      {
        "name": "champain",
        "price": "2",
        "type": "type",
        "available": "1000",
        "booked": "10"
      }
    ],
    "tickets": [
      {
        "price": "12",
        "available": "100",
        "booked": "2",
        "created_date": null
      }
    ],
    "tables": [
      {
        "price": "3",
        "type": "type",
        "available": "100",
        "booked": "10"
      }
    ],
    "photos": [
      {
        "photo": "photo"
      }
    ]
  }

  
Get event(for maker)
====================


uri: /partymaker/maker/event/get

method: GET

type: application/json

header: x-auth-token = signInMakerToken

response: array(list) of events


Get event(for dancer)
=====================


uri: /partymaker/dancer/event/get

method: GET

type: application/json

header: x-auth-token = signInDancerToken

header: zip_code = zipCodeEvent

response: array(list) of events


Revenue (tickets booked * ticket_price)
========

uri: /maker/event/revenue

method: GET

type: --//--

header: x-auth-token = signInMakerToken

header: party_name = party_name

response:

{
  "revenue": "516.0"
}

Bottles
=======

uri: /maker/event/bottles

method: GET

type: --//--

header: x-auth-token = signInMakerToken

header: party_name = party_name

response:

[
  {
    "id_bottle": 1,
    "name": "champain",
    "price": "2",
    "type": "type",
    "available": "1000",
    "booked": "10",
    "createdDate": null
  }
]

Tables
=======

uri: /maker/event/tables

method: GET

type: --//--

header: x-auth-token = signInMakerToken

header: party_name = party_name

response:

[
  {
    "id_table": 1,
    "price": "3",
    "type": "type",
    "available": "100",
    "booked": "10"
  }
]

Total
=======

uri: /maker/event/total

method: GET

type: --//--

header: x-auth-token = signInMakerToken

header: party_name = party_name

response:

{
  "withdrawn": "1200.0",
  "ticketsSales": "24.0",
  "bottleSales": "20.0",
  "tableSales": "30.0",
  "refunds": "-1126.0"
}