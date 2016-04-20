# encoding: utf-8
ENV['SSL_CERT_FILE'] = File.expand_path('ca-bundle.crt')
require 'gcm'

apikey = ''
File.open 'api_key.txt' do |file|
  apikey = file.read
end
gcm = GCM.new(apikey)

tokens = []
File.open 'tokens.txt' do |file|
  tokens.push(file.read)
end

option = {priority: 'high', data: {message: 'Wake up!'} }
response = gcm.send_notification(tokens, option)

puts response
