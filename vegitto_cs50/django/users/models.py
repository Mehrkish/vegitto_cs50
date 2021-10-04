import datetime

from django.contrib.auth.base_user import BaseUserManager
from django.contrib.auth.models import AbstractBaseUser, PermissionsMixin
from django.contrib.auth.models import Group
from django.core import validators
from django.db import models
from django.utils.deconstruct import deconstructible
from django.utils.translation import ugettext_lazy as _


@deconstructible
class UnicodeMobileNumberValidator(validators.RegexValidator):
    regex = r'09(\d{9})$'
    message = _(
        'Enter a valid mobile number.'
    )
    flags = 0


class MyUserManager(BaseUserManager):
    use_in_migrations = True

    def _create_user(self, mobile_number, email, password=None, **extra_fields):
        if not mobile_number:
            raise ValueError('The given mobile number must be set')
        email = self.normalize_email(email)
        mobile_number = self.normalize_mobile_number(mobile_number)
        user = self.model(mobile_number=mobile_number,
                          email=email, **extra_fields)
        user.set_password(password)
        user.save(using=self._db)
        return user

    def create_user(self, mobile_number, email=None, password=None, **extra_fields):
        user = self._create_user(
            mobile_number=mobile_number,
            email=email,
            password=password,
            **extra_fields,
        )
        user.is_superuser = False
        user.is_staff = False
        return user

    def create_superuser(self, mobile_number, email, password, **extra_fields):

        user = self._create_user(
            mobile_number=mobile_number,
            email=email,
            password=password,
            **extra_fields,
        )

        user.is_staff = True
        user.is_superuser = True

        if user.is_staff is not True:
            raise ValueError('Superuser must have is_staff=True.')
        if user.is_superuser is not True:
            raise ValueError('Superuser must have is_superuser=True.')

        user.save(using=self._db)
        return user

    def normalize_mobile_number(self, mobile_number):
        return mobile_number


class User(AbstractBaseUser, PermissionsMixin):
    mobile_number_validator = UnicodeMobileNumberValidator()

    first_name = models.CharField(
        _('first name'),
        max_length=20,
        blank=True,
        null=True,
    )
    last_name = models.CharField(
        _('last name'),
        max_length=20,
        blank=True,
        null=True,
    )

    username = models.CharField(_("username"), max_length=40, unique=True, null=True, blank=True)
    password = models.CharField(_('password'), max_length=165, null=True, blank=True)

    mobile_number = models.CharField(
        _('mobile number'),
        max_length=20,
        unique=True,
        null=True,
        blank=True,
        validators=[mobile_number_validator],
        error_messages={
            'unique': "A user with this mobile number already exists.",
        },
        help_text=_('(09*********)phone number')
    )

    email = models.CharField(
        _('email address'),
        max_length=40,
        unique=True,
        null=True,
        blank=True,
    )

    # 0 = men , 1 = women
    gender = models.IntegerField(
        _('gender'),
        null=True,
        blank=True,
        choices=(
            (0, 'man'),
            (1, "woman"),),
    )
    type = models.CharField(
        _("type"),
        max_length=20,
        null=True,
        blank=True,
        choices=(
            ("chief", "chief"),
            ("doctor", "doctor")
        )
    )
    groups = models.ManyToManyField(Group, verbose_name=_('groups'), blank=True)
    is_active = models.BooleanField(_('is active'), default=True)
    is_staff = models.BooleanField(_(' is staff'), default=False)
    is_superuser = models.BooleanField(_('is superuser'), default=False)
    joined_at = models.DateTimeField(_('joined at'), auto_now_add=True)
    updated_at = models.DateTimeField(_('updated at'), auto_now=True)

    USERNAME_FIELD = 'mobile_number'
    REQUIRED_FIELDS = ['email']

    objects = MyUserManager()

    def get_short_name(self):
        return self.first_name

    def get_full_name(self):
        return self.first_name + " " + self.last_name

    def __str__(self):
        if (self.first_name and self.last_name) is not None:
            return self.get_full_name()
        elif self.first_name is not None:
            return self.get_short_name()
        elif self.email is not None:
            return self.email
        elif self.mobile_number is not None:
            return self.mobile_number
        else:
            return self.id


class Province(models.Model):
    name = models.CharField(max_length=30)

    def __str__(self):
        return self.name


class City(models.Model):
    name = models.CharField(max_length=45)
    province = models.ForeignKey('Province', on_delete=models.CASCADE)

    def __str__(self):
        return self.name


def user_profile_image_path(instance, filename):
    now = datetime.datetime.now()
    return 'users/profileimage/{1}/{2}/{3}/user_{0}/{4}'.format(
        instance.user.id,
        now.strftime("%Y"),
        now.strftime("%m"),
        now.strftime("%d"),
        filename)


class Profile(models.Model):
    user = models.OneToOneField(
        'User',
        on_delete=models.CASCADE)

    profile_image = models.ImageField(
        verbose_name=_("profile image"),
        upload_to=user_profile_image_path,
        blank=True,
        null=True,
    )

    def __str__(self):
        return self.user.__str__()
