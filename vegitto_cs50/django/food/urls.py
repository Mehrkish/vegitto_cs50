from django.urls import path, include
from . import views
from rest_framework.routers import DefaultRouter


router = DefaultRouter()
router.register('foods', views.FoodViewSet, basename='foods')
router.register('ingredients', views.IngredientViewSet)
router.register('meals', views.MealViewSet)
router.register('diet-categories', views.DietCategoryViewSet)
router.register('difficulty-level', views.DifficultyLevelViewSet)

urlpatterns = [
    path('', include(router.urls)),
]
