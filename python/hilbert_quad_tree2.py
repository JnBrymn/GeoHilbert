def hilbert_point(vals,depth,mins=[],maxs=[]):
    """
    cell_names

    3 ---- 2
          |    
    0 ---- 1


    second generation

    33    30 -- 23 -- 22
    |     |           |
    32 -- 31    20 -- 21
                |
    01 -- 02    13 -- 12
    |     |           |
    00    03 -- 10 -- 11


    """
    if(mins):
        # then convert vals to some place in 1-by-1 grid at the origin
        vals[0] = (vals[0]-mins[0])/(maxs[0]-mins[0])
        vals[1] = (vals[1]-mins[1])/(maxs[1]-mins[1])
    
    if(depth == 0):
        return ''

    if( vals[0]<=0.5 and vals[1]<=0.5 ):
        cell_name = '0'
        x = vals[1]*2
        y = vals[0]*2
    elif( vals[0]>=0.5 and vals[1]<=0.5 ):
        cell_name = '1'
        x = (vals[0] - 0.5)*2
        y = vals[1]*2
    elif( vals[0]>=0.5 and vals[1]>=0.5 ):
        cell_name = '2'
        x = (vals[0] - 0.5)*2
        y = (vals[1] - 0.5)*2
    elif( vals[0]<=0.5 and vals[1]>=0.5 ):
        cell_name = '3'
        x = 1 - (vals[1] - 0.5)*2
        y = 1 - vals[0]*2

    return cell_name + hilbert_point([x,y],depth-1)

def concat_quads(quad_array):
    quad_array.sort() #this will not have to be done in practice because the quads will come in in order. Right?
    start = quad_array[0]
    last_val = start
    end = ''
    query_list=[]
    for val in quad_array[1:]:
        max_len = max(len(last_val),len(val))
        dist = int(val.ljust(max_len,'0'),4)-int(last_val.ljust(max_len,'3'),4)
        if( dist == 1 ) :
            end = val
        else :
            if( len(end) > 0 ) :
                query_list.append("["+ start +" TO "+ end +"*]")
            else :
                query_list.append(start +"*")
            end = ''
            start = val
        last_val = val
    if( len(end) > 0 ) :
        query_list.append("["+ start +" TO "+ end +"*]")
    else :
        query_list.append(start +"*")
    return query_list


if __name__ == "__main__":
    import sys
    x = 0.125435
    y = 0.85432
    depth = 8
    print "hilbert_point([%f,%f], %f, [0,0], [1,1])" % (x,y,depth)
    print hilbert_point([x,y], depth, [0,0], [1,1])
    print 
    print "concat_quads(['022','023','102','112','113','12','13','2','30','31'])"
    print concat_quads(['022','023','102','112','113','12','13','2','30','31'])










