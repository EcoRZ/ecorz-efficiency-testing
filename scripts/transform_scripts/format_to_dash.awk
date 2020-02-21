#!/usr/bin/awk -f

BEGIN {
    print "Idle time as suggested by the docs gets set to 0" > "/dev/stderr"
    print "Connect param gets generated randomly atm!" > "/dev/stderr"
    FS=","
    OFS=","
}
NR == 1 {
    $5="responseMessage"OFS$5
    $7="failureMessage"OFS$7
    $11="IdleTime"OFS"Connect"
    NF=NF-2
    print
}
NR > 1  {
    responseMessage="ERROR"
    failureMessage="failed"
    if(match($4, /^2[0-9][0-9]$/)) {
        responseMessage="OK"
        failureMessage=""
    }

    idle_time=0
    connect_rand=int(rand()*4)

    $5=responseMessage OFS $5
    $7=failureMessage OFS $7
    $11=idle_time OFS connect_rand
    NF=NF-2
    print
}
