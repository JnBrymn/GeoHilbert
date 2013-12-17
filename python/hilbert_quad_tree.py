#first index is the direction of the loop
#second index is the cell that we lie in
#value is the loop direction in the next iteration
direction_mapping = [ [ 1, 0, 3, 0 ], [ 0, 2, 1, 1 ], [ 2, 1, 2, 3 ], [ 3, 3, 0, 2 ] ]

cell_mapping = [ [ 0, 1, 3, 2 ], [ 0, 3, 1, 2 ], [ 2, 3, 1, 0 ], [ 2, 1, 3, 0 ] ]

def hilbert_point(mins,maxs,vals,depth,direction=0):
    """
number of cell and direction of closed part of loop:
      1
    2 ^ 3
  2 <   > 0
    0 v 1
      3
    """
    if(depth == 0) :
        return ''
    mids = []
    mids.append( (maxs[0] - mins[0])/2.0 + mins[0] )
    mids.append( (maxs[1] - mins[1])/2.0 + mins[1] )

    if( vals[1] <= mids[1] ) : #what to do about points exactly on boundaries?
        if( vals[0] <= mids[0] ) :
            #cell_num = 0 (by chart above)
            return str(cell_mapping[direction][0]) + hilbert_point( [mins[0], mins[1]], [mids[0], mids[1]], vals, depth-1, direction_mapping[direction][0] )
        else :
            #cell_num = 1
            return str(cell_mapping[direction][1]) + hilbert_point( [mids[0], mins[1]], [maxs[0], mids[1]], vals, depth-1, direction_mapping[direction][1] )
    else :
        if( vals[0] <= mids[0] ) :
            #cell_num = 2
            return str(cell_mapping[direction][2]) + hilbert_point( [mins[0], mids[1]], [mids[0], maxs[1]], vals, depth-1, direction_mapping[direction][2] )
        else :
            #cell_num = 3
            return str(cell_mapping[direction][3]) + hilbert_point( [mids[0], mids[1]], [maxs[0], maxs[1]], vals, depth-1, direction_mapping[direction][3] )




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
    print "hilbert_point([0,0], [1,1], [0.125435,0.85432], 8)"
    print hilbert_point([0,0], [1,1], [0.125435,0.85432], 8)
    print 
    print "concat_quads(['022','023','102','112','113','12','13','2','30','31'])"
    print concat_quads(['022','023','102','112','113','12','13','2','30','31'])










