from rest_framework import serializers

from .models import Post
from users.serializers import PublicUserSerializer
from food.serializers import FoodDetailSerializer, FoodListSerializer


class PostSerializer(serializers.ModelSerializer):
    user = PublicUserSerializer(read_only=True)
    food = FoodDetailSerializer(read_only=True)

    class Meta:
        model = Post
        fields = '__all__'


class PostListSerializer(serializers.ModelSerializer):
    user = PublicUserSerializer(read_only=True)
    food = FoodListSerializer(read_only=True)

    class Meta:
        model = Post
        fields = '__all__'


class PostCreateSerializer(serializers.ModelSerializer):
    class Meta:
        model = Post
        fields = ('id', 'caption', 'food')
