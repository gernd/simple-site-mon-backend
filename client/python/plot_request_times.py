import requests
from plotly.offline import plot
import plotly.graph_objs as go

r = requests.get('http://localhost:8081/monitor_results/1')
print(r.json())
# build traces for plotting from monitoring data
request_times = list()
timestamps = list()
timestamp  = 0
url = r.json()[0]['urlToMonitor']['url']
for monitoring_data in r.json():
    request_time = monitoring_data['timeNeededForRequest']
    request_times.append(request_time)
    timestamps.append(timestamp)
    timestamp = timestamp + 1

plot([go.Scatter(x = timestamps, y = request_times, name = 'THE NAME'), go.Scatter(x = timestamps, y = request_times, name =
    'THE OTHER NAME')], filename='request_times.html')
