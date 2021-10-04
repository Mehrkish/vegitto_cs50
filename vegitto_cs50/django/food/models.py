from django.db import models


class DifficultyLevel(models.Model):
    level_name = models.CharField(max_length=10, primary_key=True, default='not known')

    def __str__(self):
        return self.level_name


class Meal(models.Model):
    name = models.CharField(max_length=25, primary_key=True)

    def __str__(self):
        return self.name


class Food(models.Model):
    name = models.CharField(max_length=100)
    calorie = models.PositiveIntegerField(blank=True, null=True)
    nationality = models.CharField(max_length=100, blank=True, null=True)
    time = models.PositiveIntegerField(blank=True, null=True)
    difficulty_level = models.ForeignKey(DifficultyLevel, on_delete=models.SET_NULL, null=True, blank=True)
    meal = models.ManyToManyField('Meal', related_name="foods", blank=True)
    diet_category = models.ManyToManyField('DietCategory', related_name="foods", blank=True)

    def __str__(self):
        return self.name


class StepRecipe(models.Model):
    step_num = models.IntegerField()
    recipe = models.CharField(max_length=300)
    food = models.ForeignKey('Food', related_name='step_recipe', on_delete=models.CASCADE)
    
    def __str__(self):
        return '%d - %s' % (self.step_num, self.recipe)


class Ingredient(models.Model):
    name = models.CharField(max_length=100, primary_key=True)
    food = models.ManyToManyField('Food', related_name='ingredients', blank=True,
                                  through="FoodIngredient")

    def __str__(self):
        return self.name


class FoodIngredient(models.Model):
    amount = models.CharField(max_length=50, blank=True, null=True)
    ingredient = models.ForeignKey(Ingredient, related_name='food_ingredients', on_delete=models.PROTECT)
    food = models.ForeignKey(Food,  related_name='food_ingredients', on_delete=models.CASCADE)

    def __str__(self):
        return '%s %s' % (self.food.name, self.ingredient.name)


class DietCategory(models.Model):
    name = models.CharField(max_length=50)
    short_description = models.TextField(blank=True, null=True)

    def __str__(self):
        return self.name


