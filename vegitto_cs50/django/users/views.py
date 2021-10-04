from rest_framework.response import Response
from rest_framework import status
from rest_framework.views import APIView
from django.contrib.auth import authenticate
from rest_framework_simplejwt.tokens import RefreshToken
from rest_framework import viewsets
from rest_framework.filters import SearchFilter
from django_filters.rest_framework import DjangoFilterBackend

import re
from .serializers import RegistrationSerializer, PublicUserSerializer
from . models import Profile, User


class SignupView(APIView):
    def post(self, request):

        if not set(request.data.keys()) == {'username', 'user_input', 'password', 'confirm_password'}:
            return Response('bad request!', status=status.HTTP_400_BAD_REQUEST)

        user_input = request.data['user_input']
        if re.match('09(\d{9})$', user_input):
            request.data['mobile_number'] = user_input
            request.data['email'] = ''
        elif re.match('^([a-zA-Z0-9_\-.]+)@([a-zA-Z0-9_\-.]+)\.([a-zA-Z]{2,5})$', user_input):
            request.data['email'] = user_input
            request.data['mobile_number'] = ''
        else:
            return Response('ورودی صحیح نیست!', status=status.HTTP_400_BAD_REQUEST)

        del request.data['user_input']

        serializer = RegistrationSerializer(data=request.data)
        if serializer.is_valid():

            account = serializer.save()
            jwt_token = get_jwt_tokens_for_user(account)
        else:
            data = serializer.errors
            return Response(data, status=status.HTTP_400_BAD_REQUEST)

        return Response(jwt_token, status=status.HTTP_201_CREATED)


def get_jwt_tokens_for_user(user):
    refresh = RefreshToken.for_user(user)
    return {
        'refresh': str(refresh),
        'access': str(refresh.access_token),
    }


class LoginView(APIView):
    def post(self, request, format=None):
        data = request.data
        # this username will be a email or mobile number
        user_input = data.get('user_input')
        if user_input == None:
            return Response('you have to enter your email or mobile number!', status=status.HTTP_400_BAD_REQUEST)
        password = data.get('password')
        user = authenticate(username=user_input, password=password)
        if user:
            jwt_token = get_jwt_tokens_for_user(user)
            return Response(jwt_token, status=status.HTTP_200_OK)
        else:
            return Response('username or password not correct!', status=status.HTTP_404_NOT_FOUND)


class PublicProfileViewSet(viewsets.ModelViewSet):
    queryset = User.objects.all()
    serializer_class = PublicUserSerializer
    http_method_names = ['get', 'list']
    filter_backends = (SearchFilter, DjangoFilterBackend)
    search_fields = ['username']
    filterset_fields = ['username', 'id']
