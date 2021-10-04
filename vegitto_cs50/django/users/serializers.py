from rest_framework import serializers
from .models import User, Profile
from django.contrib.auth import password_validation


class RegistrationSerializer(serializers.ModelSerializer):
    confirm_password = serializers.CharField(style={'input_type': 'password'}, write_only=True)

    class Meta:
        model = User
        fields = ('id', 'username', 'mobile_number', 'email', 'password', 'confirm_password')

    def validate_password(self, value):
        password_validation.validate_password(value, self.instance)
        return value

    def save(self):
        username = self.validated_data['username']
        if self.validated_data['mobile_number']:
            account = User(
                username=username,
                mobile_number=self.validated_data['mobile_number'],
            )
        elif self.validated_data['email']:
            account = User(
                username=username,
                email=self.validated_data['email'],
            )

        password = self.validated_data['password']
        confirm_password = self.validated_data['confirm_password']

        if password != confirm_password:
            raise serializers.ValidationError({'password': 'Passwords not match!'})
        account.set_password(password)
        account.save()

        return account


class PublicProfileSerializer(serializers.ModelSerializer):
    class Meta:
        model = Profile
        fields = ['id', 'profile_image']


class PublicUserSerializer(serializers.ModelSerializer):
    profile = PublicProfileSerializer(many=False, read_only=True)

    class Meta:
        model = User
        fields = ['id', 'username', 'joined_at', 'profile']
