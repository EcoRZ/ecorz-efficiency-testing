#!/usr/bin/env python

# Python version of https://gist.github.com/tonycoco/661615

import csv
import sys

import untangle

usage = '''
Usage:
    python foo.py source_xml_file target_csv_file
'''

try:
    xml_file = sys.argv[1]
    csv_file = sys.argv[2]
except IndexError:
    print(usage)
    sys.exit(1)

#  http://jmeter.apache.org/usermanual/listeners.html#attributes
name_map =  dict([
    ('ts', 'timeStamp'),
    ('t', 'elapsed'),
    ('lb', 'label'),
    ('rc', 'responseCode'),
    # ('rm', 'responseMessage'),
    ('tn', 'threadName'),
    ('s', 'success'),
    # ('fm', 'failureMessage'),
    ('by', 'bytes'),
    ('ng', 'grpThreads'),
    ('na', 'allThreads'),
    ('lt', 'Latency'),
    # ('it', 'IdleTime'),
    # ('cn', 'Connect'),
    ('ec', 'ErrorCount'),
    ('hn', 'HostName'),
    ('sc', 'SampleCount')
])

obj = untangle.parse(xml_file)

samples = [
    {name_map.get(k, k):v for k, v in sample._attributes.items()} 
    for sample in obj.testResults.httpSample
]
#fieldnames = samples[0].keys() + ['failureMessage']
fieldnames = [
    'timeStamp','elapsed','label','responseCode',
    'threadName','success', 'bytes','grpThreads','allThreads',
    'Latency', 'ErrorCount', 'HostName', 'SampleCount']

with open(csv_file, 'w+') as fd:
    writer = csv.DictWriter(fd, fieldnames)
    writer.writeheader()
    writer.writerows(samples)
