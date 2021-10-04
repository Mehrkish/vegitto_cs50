from django.contrib import admin


from social.models import Comment, HighlightedPost, Post, Files

admin.site.register(Comment)
admin.site.register(Post)
admin.site.register(HighlightedPost)
admin.site.register(Files)
