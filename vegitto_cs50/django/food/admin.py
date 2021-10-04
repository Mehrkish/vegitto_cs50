from django.contrib import admin


from food.models import Food, FoodIngredient, Ingredient, DietCategory, Meal, DifficultyLevel, StepRecipe


@admin.register(Food)
class FoodAdmin(admin.ModelAdmin):
    model = Food
    search_fields = ['name']
    list_display = ['id', 'name', 'get_meal', 'get_diet_category', 'difficulty_level', 'calorie']
    list_filter = ['diet_category__name', 'meal']
    readonly_fields = ['id']

    def get_diet_category(self, obj):
        return "\n".join([d.name for d in obj.diet_category.all()])

    def get_meal(self, obj):
        return "\n".join([m.name for m in obj.meal.all()])


@admin.register(Ingredient)
class IngredientAdmin(admin.ModelAdmin):
    search_fields = ['name']
    list_display = ['name']


@admin.register(DifficultyLevel)
class DifficultyLevelAdmin(admin.ModelAdmin):
    search_fields = ['level_name']
    list_display = ['level_name']

@admin.register(Meal)
class MealAdmin(admin.ModelAdmin):
    search_fields = ['name']
    list_display = ['name']


@admin.register(FoodIngredient)
class FoodIngredientAdmin(admin.ModelAdmin):
    list_display = ['id', 'food', 'ingredient', 'amount']
    readonly_fields = ['id']


@admin.register(DietCategory)
class DietCategoryAdmin(admin.ModelAdmin):
    search_fields = ['id', 'name']
    list_display = ['name']
    readonly_fields = ['id']


@admin.register(StepRecipe)
class StepRecipeAdmin(admin.ModelAdmin):
    list_display = ('id', 'food', 'step_num', 'recipe')
    search_fields = ('id', 'food__name', 'recipe')
    ordering = ('-food',)




