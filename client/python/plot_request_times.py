import requests
from plotly.offline import plot
import plotly.graph_objs as go

def build_data_for_monitored_url(id):
    '''Fetches and prepares data for plotting for the given URL id'''
    r = requests.get('http://localhost:8081/monitor_results/' + str(id))
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
    return go.Scatter(x = timestamps, y = request_times, name = url)

# get all monitored sites and fetch data for it
r = requests.get('http://localhost:8081/monitored-sites')
plotting_data = list()
for monitored_site in r.json():
    print('Fetching data for ' + monitored_site['url'])
    data_for_site = build_data_for_monitored_url(monitored_site['id'])
    plotting_data.append(data_for_site)

plot(plotting_data, filename='request_times.html')
