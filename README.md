# Vegitto

## What is Vegitto?
Vegitto is a community devoted to those in favor of non-meat diets: Vegetarianism and Veganism. It provides its users with a platform (a social media) where they would be able to connect, share their knowledge and support one another in this challenging journey of Veganism.

To meet the primary requirements of such a platform, we decided to focus on the most basics, yet necessary features. During this short period, we could build an android app where our users can share their food recipes and view others' shared recipes.

Here are some of the main components of our android project (*In this project we use MVVP architecture*):

# Android
>## Models

>>### Food.java
This class is a model for our food objects. Every food includes many variables such as id, name, ingredient and etc.

The food class itself contains two classes: Ingredient and StepRecipe

>>>#### Ingredient
This is a model designed to hold an instance of every ingredients of a food.

>>>#### StepRecipe
This is basically a model made to hold an instance of each step of a recipe.

>>### FoodResponse.java
This class is a holder for the response (foods) recieved from server.

>>### UserPrivateInfo.java
This class holds the private information of our users, which shall not be shared with oothers.

>## Views and ViewModels
The project is made of two activities:
- MainActivity: Most of main activities are occured here. 
- RegistryActivity: LogIn and SignUp processes are handled here.

>>### MainActivity
This consists of four main parts:
- HomepageFragment.java along with its view models
- NotificationFragment.java
- ProfileFragment.java along with its view models
- PostingFragment, where users create posts.

>>### RegistryActivity
This activity nests two main fragments:
- LogInFragment.java
- SignUpFragment.java.

*More information regarding these fragments and their compoents will be given in the following parts.*

There is another package in our project called roomDB. Here all the processe regarding saving the most recent foods' data are saved for the sake of when the internet is down.

Now we go through how the *Backend* works.

# BACKEND
In this project we used Django framework for back-end part. also, used REST API for transfering data to clients. we use Android client for showing data to clients. 

## Django folder apps
> users
> social
> food
> green (django project name) 

### users
in this app we have authentication, viewing profiles. We use JWT authentication for users
- views: we use APIView of Django Rest Framework and have signup view and login view and showing profile
- models: we have database tables and fields include User, Profile, Province, City table.
- serializers: It used to serialize data from view to database and database to view
- admin: for showing database data into auto admin page generation of Django framework

### social
in this app we have Posts, Comments, Files. you can share foods in form of posts.
- views: we use APIView and ModelViewset of Django Rest Framework. we have create post and show detail of post endpoints.
- models: we have database tables and fields include Post, Comment, Files(images,videos), HighlightedPost.
- serializers: It used to serialize data from view to database and database to view
- admin: for showing database data into auto admin page generation of Django framework
- permission: have some permissions for editing posts whom only have prevalent to do that.

### food
in this app we have foods details include ingredients and their steps 
- views: we use ModelViewset of Django Rest Framework. we have food, meal, dietcategory, ingredient and step recipes detail endpoints.
- models: tables include Meal, Food, Ingredients, FoodIngredients, DietCategories, StepRecipies
- serializers: It used to serialize data from view to database and database to view
- admin: for showing database data into auto admin page generation of Django framework

### green
This is setting folder 
- settings: basic django settings 

## Installation

requires python v3+ to run.

Install the dependencies and devDependencies and start the server.

sh
pip install -r requirements.py


For production environments...

sh
python manage.py migrate
python manage.py runserver


your server address is
sh
127.0.0.1:8000
