from django.shortcuts import get_object_or_404, render, redirect
from django.http import HttpResponse,JsonResponse
# from .models import Question
import requests
from django.contrib import auth
from django.contrib.auth.decorators import login_required
from django.views.decorators.cache import cache_control,never_cache
from django.utils.cache import add_never_cache_headers
p=''
# Create your views here.
#def index(request):
#   data=Question.objects.order_by('-pub_date')[:5]
#   context={
#       'question_list':data
#   }
#    return render(request, 'index.html', context)

#def detail(request,question_id):
#   return HttpResponse("You are lokking at question %s" % question_id)


def login(request):
    context={'data':''}
    return render(request,'login1.html',context)

#def invalid_login(request):
    #return render(request,'invalid_login.html')
@never_cache
def home(request):
    if (request.method)=="POST":
        id1=request.POST['id']
        name=request.POST['username']
        password=request.POST['password']
        det = { 'id':id1,'name':name,'password':password}
        token = requests.post("https://sport-resources-booking-api.herokuapp.com/AdminLogin",det)
        global p
        p = token.json()['access_token']
        return redirect('home')
        '''if(p=="Invalid credentials"):
            context={'data':"INVALID CREDENTIALS"}
            return render(request,'login1.html',context)
            return HttpResponse(p)'''
        '''else:
            data = requests.get("https://sport-resources-booking-api.herokuapp.com/ResourcesPresent", headers = {'Authorization':f'Bearer {p}'}) 
            res = data.json()
            context={'data': res,}
            return redirect('resources')'''
    elif (request.method)=="GET":
        if(p):
            if(p=="Invalid credentials"):
                print('invalid')
                context={'data':"INVALID CREDENTIALS"}
                return render(request,'login1.html',context)
            #return HttpResponse(p)
            else:
                print('valid')
                data = requests.get("https://sport-resources-booking-api.herokuapp.com/ResourcesPresent", headers = {'Authorization':f'Bearer {p}'}) 
                res = data.json()
                context={'data': res,}
                return redirect('resources')
        else:
            return redirect('login')
@never_cache
def cPassword(request):
    if(p):
        if (request.method)=="POST":
            id1=int(request.POST['id1'])
            pword=request.POST['old_password']
            new_pword=request.POST['password']
            confirm_pword=request.POST['confirm_password']
            if(new_pword==confirm_pword):
                td=requests.post('https://sport-resources-booking-api.herokuapp.com/admin_change_password',headers={'Authorization':f'Bearer {p}'},
                data={'id':id1,'password':pword,'new_password':confirm_pword})
                res = td.json()
                context={'data1':res['message']}
                return render(request,'updatePassword.html',context)
            else:
                context={"data1":"confirm password doesn't match with your new password"}
                return render(request,'updatePassword.html',context)
        else:
            return redirect('home')
    else:
            return redirect('login')
@never_cache
def api_call(request):
    global p
    if(p):
        data = requests.get("https://sport-resources-booking-api.herokuapp.com/ResourcesPresent", headers = {'Authorization':f'Bearer {p}'}) 
        res = data.json()
        if(p==''):
            return redirect('login')
        else:
            context={'data': res,} 
            return render(request,'api.html',context)
    else:
        return redirect('login')
@never_cache
def about(request):
    if(p):
        return render(request,'about.html')
    else:
        return redirect('login')

@never_cache
def updatePassword(request):
    if(p):
        return render(request,'updatePassword.html')
    else:
        return redirect('login')

def incOne(request):
    id = request.GET['id']

    global p
    td=requests.get('https://sport-resources-booking-api.herokuapp.com/incrementByValue',headers={'Authorization':f'Bearer {p}'},
    data={'id':id,'c':1})
    res = td.json()
    return JsonResponse(res,safe=False)

def decOne(request):
    id = request.GET['id']

    global p
    td=requests.get('https://sport-resources-booking-api.herokuapp.com//decrementByValue',headers={'Authorization':f'Bearer {p}'},
    data={'id':id,'c':1})
    res = td.json()
    return JsonResponse(res,safe=False)
@never_cache
def bookingRequests(request):
    global p
    if(p):
        search_term = ''
        if 'search' in request.GET:
            search_term = request.GET['search']
        td = requests.get("https://sport-resources-booking-api.herokuapp.com/bookingRequests", headers = {'Authorization':f'Bearer {p}'},
        data={'search':search_term}) 
        res = td.json()
        context={'data': res,} 
        return render(request,'bookingreq.html',context)
    else:
        return redirect('login')

def reject(request):
    id = request.GET['id']

    global p
    td=requests.get('https://sport-resources-booking-api.herokuapp.com/rejectBooking',headers={'Authorization':f'Bearer {p}'},
    data={'id':id})
    res = td.json()
    return JsonResponse(res,safe=False)

def accept(request):
    id = request.GET['id']
    global p
    td=requests.get('https://sport-resources-booking-api.herokuapp.com/issueResource',headers={'Authorization':f'Bearer {p}'},
    data={'id':id})
    res = td.json()
    return JsonResponse(res,safe=False)
@never_cache
def blockedUsers(request):
    global p
    if(p):
        search_term = ''
        if 'search' in request.GET:
            search_term = request.GET['search']
        td=requests.get('https://sport-resources-booking-api.herokuapp.com/blockedUsers',headers={'Authorization':f'Bearer {p}'},
        data={'search':search_term})
        res = td.json()
        context={'data': res,}
        return render(request,'blocked.html',context)
    else:
        return redirect('login')
    #return JsonResponse(res,safe=False)
def blockUsers(request):
    id=''
    id = request.GET['id']
    fine = request.GET['fine']
    global p
    td=requests.get('https://sport-resources-booking-api.herokuapp.com/blockUser',headers={'Authorization':f'Bearer {p}'},
    data={'id':id,'fine':fine})
    res = td.json()
    return redirect('blockedUsers')

def add_resource(request):
    name = request.POST['name']
    count = int(request.POST['count'])
    global p
    td=requests.post('https://sport-resources-booking-api.herokuapp.com/AddExtraResource',headers={'Authorization':f'Bearer {p}'},
    data={'name':name,'count':count})
    res = td.json()
    return redirect('home')

def unblock(request):
    id = request.GET['id']
    global p
    td=requests.get('https://sport-resources-booking-api.herokuapp.com/unblockUser',headers={'Authorization':f'Bearer {p}'},
    data={'id':id})
    res = td.json()
    return JsonResponse(res,safe=False)
@never_cache
def bookingHistory(request):
    global p
    if(p):
        search_term = ''
        if 'search' in request.GET:
            search_term = request.GET['search']
        data1 = requests.get("https://sport-resources-booking-api.herokuapp.com/notreturnedHistory", headers = {'Authorization':f'Bearer {p}'},
        data={'search':search_term})
        data2 = requests.get("https://sport-resources-booking-api.herokuapp.com/notreturnedToday", headers = {'Authorization':f'Bearer {p}'},
        data={'search':search_term})
        data3 = requests.get("https://sport-resources-booking-api.herokuapp.com/returnedHistory", headers = {'Authorization':f'Bearer {p}'})
        res1 = data1.json()
        res2 = data2.json()
        res3 = data3.json()
        context={'data1': res1,'data2':res2,'data3':res3} 
        return render(request,'his.html',context)
    else:
        return redirect('login')

def acceptResource(request):
    id = request.GET['id']
    global p
    td=requests.get('https://sport-resources-booking-api.herokuapp.com/acceptResource',headers={'Authorization':f'Bearer {p}'},
    data={'id':id})
    res = td.json()
    return JsonResponse(res,safe=False)
@never_cache
def logout(request):
    auth.logout(request)
    global p
    p=0
    context={'data':"You logged out successfully"}
    return render(request,'login1.html',context)
@never_cache
def timetable(request):
    global p
    if(p):
        if (('branch' in request.GET) and ('year' in request.GET) and ('section' in request.GET)):
            branch = request.GET['branch']
            year = request.GET['year']
            section = request.GET['section']
            data1 = requests.get("https://sport-resources-booking-api.herokuapp.com/timetable", headers = {'Authorization':f'Bearer {p}'},
            data={'branch':branch,'year':year,'section':section})
            data1 = data1.json()
            context={'data': data1,}
            return render(request,'timetable.html',context)
        else:
            return render(request,'timetable.html')
    else:
        return redirect('login')