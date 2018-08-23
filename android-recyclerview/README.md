Android | Create a List with RecyclerView
=========================================

![android-recyclerview-app-layout](http://hmkcode.github.io/images/android/android-recyclerview-app_layout.png)


Creating a scrollable list of elements is a common pattern in mobile application. Using RecyclerView we can list a large data sets or frequently changing one. `RecyclerView` is an advanced and flexible version of ListView, addressing serveral issues with existing listing views. Here we will build a simple application with `RecyclerView`

## Overview

We will build a simple app that lists a hard-coded `Link` in a `RecyclerView`. To display items on `RecyclerView` you need to have the following components:

- `RecyclerView` widget added to the activity layout.  
- A class extending `RecyclerView.Adapter`. 
- A class extending `RecyclerView.ViewHolder`. 
- Layout for RecyclerView items.  

Files we need for this app are shown in the image below. 

![android-recyclerview-app-files](http://hmkcode.github.io/images/android/android-recyclerview-app-files.png)