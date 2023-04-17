# BeerBox 

BeerBox is a beer catalog sample Android application, where you can search and view all of Brewdog's beers.

### ⬇️ Try out the app, download the apk from the link below:

[![BeerBox](https://img.shields.io/badge/BeerBox-v1.0.0-%2306090E?style=for-the-badge&logo=android)]()

## Architecture :
This project is based on the CleanArchitecture

 ```
com.giacomoparisi.beerbox
├── 📂 app/                          # App module that contains Application, Activity and 
|   ├── AppModule.kt                   main Dagger Module
|   ├── MainActivity.kt
│   └── App.kt
│
├── 📂 buildSrc/                     # Dependency versions, and projects settings organization
│
├── 📂 core/                         # Contains all possible shared UI and utilities for all 
|                                      presentation modules
│                       
├── 📂 data/                         # Module that interacts with external services like 
│                                      network or database
│                                     
├── 📂 domain/                       # Contains the business logic, also it is responsible 
|                                      for processing the data from the data layer to the 
|                                      presentation layer and viceversa
│                           
├── 📂 entities/                     # Contains all the data classes used by the business logic 
│   
└── 📂 home/                         # Presentation(UI) module for the home screen

```

### SCREENS

<table class="tg">
<thead>
  <tr>
    <th class="tg-0pky">SPLASH</th>
    <th class="tg-0pky">HOME</th>
  </tr>
</thead>
<tbody>
  <tr>
    <th class="tg-0pky"><img src="images/splash_screen.png" width=30%></th>
    <th class="tg-0pky"><img src="images/home.png" width=30%></th>
  </tr>
</tbody>
</table>

### FEATURES

<table class="tg">
<thead>
  <tr>
    <th class="tg-0pky">SEARCH</th>
    <th class="tg-0pky">FILTERS</th>
  </tr>
</thead>
<tbody>
  <tr>
    <th class="tg-0pky"><img src="images/search.png" width=30%></th>
    <th class="tg-0pky"><img src="images/filters.png" width=30%></th>
  </tr>
 <thead>
  <tr>
    <th class="tg-0pky">EMPTY SEARCH</th>
    <th class="tg-0pky"></th>
  </tr>
</thead>
 <tr>
    <th class="tg-0pky"><img src="images/no_results.png" width=30%></th>
    <th class="tg-0pky"></th>
 </tr>
</tbody>
</table>

### LOADING

<table class="tg">
<thead>
  <tr>
    <th class="tg-0pky">MAIN LOADING</th>
    <th class="tg-0pky">NEXT PAGE LOADING</th>
  </tr>
</thead>
<tbody>
  <tr>
    <th class="tg-0pky"><img src="images/loading.png" width=30%></th>
    <th class="tg-0pky"><img src="images/list_loading.png" width=30%></th>
  </tr>
</tbody>
</table>

### ERROR

<table class="tg">
<thead>
  <tr>
    <th class="tg-0pky">MAIN ERROR</th>
    <th class="tg-0pky">NEXT PAGE ERROR</th>
  </tr>
</thead>
<tbody>
  <tr>
    <th class="tg-0pky"><img src="images/error.png" width=30%></th>
    <th class="tg-0pky"><img src="images/list_error.png" width=30%></th>
  </tr>
</tbody>
</table>


### FEATURES

#### SPLASH SCREEN

<img src="images/splash_screen.png" width=20% height=20%>

#### HOME
<img src="images/home.png" width=20% height=20%>

#### SEARCH
<img src="images/search.png" width=20% height=20%> 

#### FILTERS
<img src="images/filters.png" width=20% height=20%>

#### LOADING
<img src="images/loading.png" width=20% height=20%>

#### LOADING ITEM
<img src="images/list_loading.png" width=20% height=20%>


### Credits 
The API used by this project are provided by **[Punk Api](https://punkapi.com/)**

## License
```
 Copyright 2023 Giacomo Parisi

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
