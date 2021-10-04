from django.db import models
from django.contrib.contenttypes.fields import GenericForeignKey
from django.contrib.contenttypes.models import ContentType
from django.contrib.contenttypes.fields import GenericRelation
import datetime


class Comment(models.Model):
    commenter = models.ForeignKey('users.User', on_delete=models.CASCADE)
    content_type = models.ForeignKey(ContentType, on_delete=models.CASCADE)
    object_id = models.PositiveIntegerField()
    content_object = GenericForeignKey('content_type', 'object_id')
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)
    text = models.TextField(blank=True, null=True)

    def __str__(self):
        return '%s - %s' % (self.commenter, self.text)


def files_path(instance, filename):
    now = datetime.datetime.now()
    return 'social/files/{1}/{2}/{3}/user_{0}/{4}'.format(
        instance.user.id,
        now.strftime("%Y"),
        now.strftime("%m"),
        now.strftime("%d"),
        filename)


class Files(models.Model):
    file = models.FileField(blank=True, null=True, upload_to=files_path)
    user = models.ForeignKey('users.User', related_name='files', on_delete=models.CASCADE)
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)
    content_type = models.ForeignKey(ContentType, on_delete=models.CASCADE)
    object_id = models.PositiveIntegerField()
    content_object = GenericForeignKey('content_type', 'object_id')

    def __str__(self):
        return self.user.__str__()


class Post(models.Model):
    user = models.ForeignKey('users.User', related_name='posts', on_delete=models.CASCADE)
    food = models.ForeignKey('food.Food', related_name='posts', on_delete=models.CASCADE)
    caption = models.TextField(blank=True, null=True)
    comment = GenericRelation(Comment, related_query_name='post')
    files = GenericRelation(Files, related_query_name='post')
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

    def __str__(self):
        return '%d' % self.id


class HighlightedPost(models.Model):
    user = models.ForeignKey('users.User', related_name='highlight', on_delete=models.CASCADE)
    posts = models.ManyToManyField('Post', blank=True)
    title = models.CharField(max_length=25, blank=True, null=True)
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

    def __str__(self):
        return '%s - %s' % (self.user, self.posts)

