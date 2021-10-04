from rest_framework import viewsets
from rest_framework.pagination import LimitOffsetPagination
from .serializers import FoodIngredientSerializer, FoodDetailSerializer, FoodListSerializer, IngredientSerializer, \
    MealSerializer, DietCategorySerializer, DietCategorySerializer, DifficultyLevelSerializer

from .models import Food, Ingredient, Meal, DietCategory, DifficultyLevel
from rest_framework.filters import SearchFilter
from django_filters.rest_framework import DjangoFilterBackend


class FoodViewSet(viewsets.ModelViewSet):
    """Handle Get Post and List request for food"""

    # serializer_class = FoodDetailSerializer
    queryset = Food.objects.all()
    http_method_names = ['get', 'list', 'post', 'patch']
    pagination_class = LimitOffsetPagination
    filter_backends = (SearchFilter, DjangoFilterBackend)
    filterset_fields = ['meal__name', 'diet_category__name',
                        'nationality', 'difficulty_level__level_name', 'ingredients']
    search_fields = ['name']

    def get_serializer_class(self):
        print(self.action)
        if self.action == "list":
            return FoodListSerializer
        return FoodDetailSerializer


class FoodByIngredientsViewSet(viewsets.ModelViewSet):
    """ for search foods by ingredient name"""
    serializer_class = FoodDetailSerializer
    queryset = Food.objects.all()
    filter_backends = (SearchFilter, DjangoFilterBackend)
    filterset_fields = ['meal__name', 'diet_category__name',
                        'nationality', 'difficulty_level__level_name']
    search_fields = ['ingredients__name']
    http_method_names = ['get', 'list']


class IngredientViewSet(viewsets.ModelViewSet):
    """Handle Get Post and List request for food"""
    serializer_class = IngredientSerializer
    queryset = Ingredient.objects.all()
    filter_backends = (SearchFilter,)
    search_fields = ['name']
    http_method_names = ['get', 'list', 'post']


class MealViewSet(viewsets.ModelViewSet):
    """Handle Get Post and List request for meal"""
    serializer_class = MealSerializer
    queryset = Meal.objects.all()
    http_method_names = ['get', 'list', 'post']


class DietCategoryViewSet(viewsets.ModelViewSet):
    """Handle Get Post and List request for diet category"""
    serializer_class = DietCategorySerializer
    queryset = DietCategory.objects.all()
    http_method_names = ['get', 'list', 'post']


class DifficultyLevelViewSet(viewsets.ModelViewSet):
    """Handle Get Post and List request for difficulty-level"""
    serializer_class = DifficultyLevelSerializer
    queryset = DifficultyLevel.objects.all()
    http_method_names = ['get', 'list', 'post']