from django.urls import path

from . import views

urlpatterns=[
	#path('index',views.index,name='index'),
	#path('<int:question_id>/',views.detail,name='detail'),
	path('login',views.login,name='login'),
	path('cPassword',views.cPassword,name='cPassword'),
	path('updatePassword',views.updatePassword,name='updatePassword'),
	path('home',views.home,name='home'),
	path('resources',views.api_call,name='resources'),
	path('about',views.about,name='about'),
	path('incOne',views.incOne,name='incOne'),
	path('decOne',views.decOne,name='decOne'),
	path('bookingHistory',views.bookingHistory,name='bookingHistory'),
	path('acceptResource',views.acceptResource,name='acceptResource'),
	path('bookingRequests',views.bookingRequests,name='bookingRequests'),
	path('reject',views.reject,name='reject'),
	path('accept',views.accept,name='accept'),
	path('blockedUsers',views.blockedUsers,name='blockedUsers'),
	path('blockUsers',views.blockUsers,name='blockUsers'),
	path('unblock',views.unblock,name='unblock'),
	path('logout',views.logout,name='logout'),
	path('timetable',views.timetable,name='timetable'),
	path('add_resource',views.add_resource,name='add_resource')
]