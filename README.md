# AndroidWeatherApp


- This is an application created to display my comprehension of the Android framework.
- It demonstrates how:
	1. Information can be passed between two Activities using intents.
	2. Information can be passed between Fragments, by means of interfaces.
- The application allows a user to type in a city name and employs autocomplete to assist the user in searching for a city and makes an API call to https://openweathermap.org/api
- The received JSON is converted to a class using Gson.
- The application uses a Recycler view to display different aspects of the weather (wind, humidity, pressure) as well as a weather icon which shows an appropriate weather image (ex. clouds with rain if the weather is rainy).