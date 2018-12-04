from random import randint

def Insertion_Sort(a):
    for i in range (1,len(a)): 
        x = a[i]
        index = i
        while index > 0 and a[index -1] > x:
            a[index] = a[index - 1]
            index = index-1
        
        a[index] = x
    return a

def Bubble_Sort(a):
    for i in range (1,len(a)-1):
        swapped = False
        for j in range (0, len(a)-1):
            if a[j]>a[j+1]:
                swap = a[j]
                a[j]=a[j+1]
                a[j+1]= swap
                swapped = True
                
        
        if not swapped:
            break
        
    return a

def Merge_Sort(a):
    if len(a)>1:
        mid = len(a)//2
        lefthalf = a[:mid]
        righthalf = a[mid:]

        Merge_Sort(lefthalf)
        Merge_Sort(righthalf)
        
        i=0
        j=0
        k=0
        while i < len(lefthalf) and j < len(righthalf):
            if lefthalf[i] < righthalf[j]:
                a[k]=lefthalf[i]
                i=i+1
            else:
                a[k]=righthalf[j]
                j=j+1
            k=k+1

        while i < len(lefthalf):
            a[k]=lefthalf[i]
            i=i+1
            k=k+1

        while j < len(righthalf):
            a[k]=righthalf[j]
            j=j+1
            k=k+1

    return a

def Quick_Sort(a):
    if len(a) <= 1:
        return a
    else:
        pivot = a[0]
        less_Subarray = []
        greater_Subarray = []
        for i in range (1,len(a)):
            if a[i]<pivot:
                less_Subarray.append(a[i])
            else:
                greater_Subarray.append(a[i])
                
        result = []
        result.extend(Quick_Sort(less_Subarray))
        result.append(pivot)
        result.extend(Quick_Sort(greater_Subarray))
        
    return result

a = [randint(1,1000) for i in range(10)]
print("Original: ",a)
a=Quick_Sort(a)
print("")
print("Ordenado: ",a)
