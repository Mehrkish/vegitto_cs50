from rest_framework.response import Response
from rest_framework import status
from rest_framework.views import APIView
from rest_framework import viewsets
from rest_framework.pagination import LimitOffsetPagination
from rest_framework.filters import SearchFilter
from django_filters.rest_framework import DjangoFilterBackend
from rest_framework.permissions import IsAuthenticated

from .permissions import UpdateOwnPost
from .serializers import PostSerializer, PostCreateSerializer, PostListSerializer
from .models import Post


class PostViewSet(viewsets.ModelViewSet):
    serializer_class = PostSerializer
    queryset = Post.objects.all()
    permission_classes = (UpdateOwnPost,)
    pagination_class = LimitOffsetPagination
    filter_backends = (SearchFilter, DjangoFilterBackend)
    filterset_fields = ['user', 'food']
    http_method_names = ['get', 'list', 'delete', 'patch', 'put']

    def get_serializer_class(self):
        if self.action == "list":
            return PostListSerializer
        else:
            return PostSerializer


class PostCreateView(APIView):
    """API for creating post!"""
    permission_classes = (IsAuthenticated,)

    def post(self, request):
        keys_check = set(request.data.keys()) == {'food', 'caption'}
        if (not keys_check):
            return Response('bad request!', status=status.HTTP_400_BAD_REQUEST)

        serializer = PostCreateSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save(user=request.user)
            return Response(data=serializer.data, status=status.HTTP_201_CREATED)
        else:
            data = serializer.errors
            return Response(data, status=status.HTTP_400_BAD_REQUEST)

