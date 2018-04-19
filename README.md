# TestAssignment
This test assignment has been completed in 10 hours, including repository creation and readme file.

In such small time i did not have time to study the api, provide p roper icons and style the ui.
There are unfortunately almost no tests, and some logic still needs to be moved in the proper viewModel.

Unfortunately i could not use members injection (Dagger) and i had to use AsyncTask to execute asyncronously the api calls.
This made the architecture less efficient and more error prone to memory leaks.

I also did not have time to let the user select his  preferred unit: i added the pref helper to handle that part of the logic but i had no time to create the screen to set it.

Some distance is hardcoded and some strings are as well hardcoded.

Error handling is not in place (I added some todos.)

I choosed to use DataBinding, it makes all the ui process faster and easily to observe from a viewModel.
I also decided to use a MVVM architecture.

Of course logic could be a lot better.
For example the network is hard to test right now since i'm not passing a MyOldStyleHttpRequest but i'm creating a new instance everytime.
With member injection all those tasks get a lot easier.

I Stored the Api_KEY in the gradle properties.In this way is not accessible from the code repo.

About the help screen i tried to style a bit the ui and i added an umbrella: In the NL is mandatory!
