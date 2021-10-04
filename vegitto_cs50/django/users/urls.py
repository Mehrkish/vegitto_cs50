from django.urls import path, include
from . import views
from rest_framework.routers import DefaultRouter
from rest_framework_simplejwt.views import (
    TokenObtainPairView,
    TokenRefreshView,
)

router = DefaultRouter()

urlpatterns = [
    path('users/public-profile/', views.PublicProfileViewSet.as_view({'get': 'list'})),
    path('users/signup/', views.SignupView.as_view(), name='register'),
    path('users/login/', views.LoginView.as_view(), name='login'),
    path('token/refresh/', TokenRefreshView.as_view(), name='token_refresh'),

]
