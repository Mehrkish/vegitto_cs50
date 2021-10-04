import jdatetime
from django.contrib import admin
from django.contrib.auth.admin import UserAdmin as AuthUserAdmin
from django.contrib.auth.models import Permission
from django.utils.translation import ugettext_lazy as _

from .models import User, Profile, City, Province


class UserAdmin(AuthUserAdmin):
    fieldsets = (
        (_('Personal info'), {'fields': ('first_name', 'last_name')}),
        (None, {'fields': ('mobile_number', 'email', 'username', 'password')}),
        (_('Permissions'), {
            'fields': ('is_active', 'is_staff', 'is_superuser', 'type', 'gender', 'user_permissions', 'groups'),
        }),
        (_('Important dates'), {'fields': ('last_login',)}),
    )
    add_fieldsets = (
        (None, {
            'classes': ('wide',),
            'fields': ('mobile_number', 'password1', 'password2'),
        }),
    )

    list_display = ('id', 'username', 'mobile_number', 'email', 'type', 'is_staff')
    list_filter = ('is_staff', 'is_superuser', 'is_active', 'type', 'gender')
    search_fields = ('first_name', 'last_name', 'id', 'type', 'username',
                     'email', 'mobile_number')
    ordering = ('-id',)
    filter_horizontal = ([])


@admin.register(Profile)
class ProfileAdmin(admin.ModelAdmin):
    list_display = ('id', 'user_view')
    search_fields = ('id', 'user__mobile_number', 'user__first_name',
                     'user__last_name', 'user__email')
    list_filter = ('user__type', 'user__gender')
    ordering = ('-id',)
    autocomplete_fields = ('user',)

    def user_view(self, obj):
        return obj.user.__str__()

    def born_date_view(self, obj):
        if obj.born_date:
            return jdatetime.date.fromgregorian(date=obj.born_date)


@admin.register(City)
class CityAdmin(admin.ModelAdmin):
    list_display = ('name', 'province')
    search_fields = ('id', 'name', 'province__name')
    ordering = ('name',)


admin.site.register(Permission)
admin.site.register(User, UserAdmin)
admin.site.register(Province)
