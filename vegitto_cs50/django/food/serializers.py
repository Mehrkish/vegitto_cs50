from rest_framework import serializers
from .models import Food, Ingredient, FoodIngredient, \
    StepRecipe, DifficultyLevel, Meal, DietCategory


class DietCategorySerializer(serializers.ModelSerializer):
    class Meta:
        model = DietCategory
        fields = '__all__'


class FoodIngredientSerializer(serializers.ModelSerializer):

    class Meta:
        model = FoodIngredient
        fields = ['id', 'ingredient', 'amount']


class StepRecipeSerializer(serializers.ModelSerializer):
    class Meta:
        model = StepRecipe
        fields = ['id', 'step_num', 'recipe']


class FoodListSerializer(serializers.ModelSerializer):

    diet_category = DietCategorySerializer(many=True, read_only=True)

    class Meta:
        model = Food
        fields = (
            'id',
            'name',
            'calorie',
            'nationality',
            'time',
            'meal',
            'diet_category',
            'difficulty_level',
        )


class FoodDetailSerializer(serializers.ModelSerializer):
    step_recipes_id = serializers.PrimaryKeyRelatedField(
        write_only=True, source='step_recipe', many=True,
        queryset=StepRecipe.objects.all()
    )
    food_ingredients_id = serializers.PrimaryKeyRelatedField(
        write_only=True, source='food_ingredients', many=True,
        queryset=FoodIngredient.objects.all()
    )
    diet_categories_id = serializers.PrimaryKeyRelatedField(
        write_only=True, source='diet_category', many=True,
        queryset=DietCategory.objects.all()
    )

    step_recipe = StepRecipeSerializer(many=True, read_only=True)
    food_ingredients = FoodIngredientSerializer(many=True, read_only=True)
    diet_category = DietCategorySerializer(many=True, read_only=True)

    class Meta:
        model = Food
        fields = (
            'id',
            'name',
            'calorie',
            'nationality',
            'time',
            'meal',
            'diet_category',
            'difficulty_level',
            'food_ingredients',
            'step_recipe',
            'step_recipes_id',
            'food_ingredients_id',
            'diet_categories_id',
        )


class IngredientSerializer(serializers.ModelSerializer):
    class Meta:
        model = Ingredient
        fields = '__all__'


class MealSerializer(serializers.ModelSerializer):
    class Meta:
        model = Meal
        fields = '__all__'


class DifficultyLevelSerializer(serializers.ModelSerializer):
    class Meta:
        model = DifficultyLevel
        fields = '__all__'
