import requests

r = requests.get('http://localhost:8081/monitor_results/1')
print(r.json())
for monitoring_data in r.json():
    print 'URL: ' +  monitoring_data['urlToMonitor']['url']
