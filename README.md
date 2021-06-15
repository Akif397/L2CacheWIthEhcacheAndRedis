# L2CacheWithEhcacheAndRedis

Every system use caching. And in the caching system we use ehcachev3 and redis cache. That's why the micro service called itself L2CacheWithEhcacheAndRedis.

In this branch we introduce scheduler with redis cache .

This micro service work flow is given below.

# In this micro service we are implementing a todo system by using level 2 caching
# We initialize a database named "cache_manager" by using mysql
# In the database we currently initializing one table only named "items".

# While we are adding a todo we just directly put it into the database.
# But while we are fetching the items that are in the todo's we divide that into two parts.
  1. We can fetch the todo's ony by one.
  2. We can fetch them altogether.
  
# When we create a todo we save the data in ehcache and redis. We save it in ehcache for 10 seconds and in redis for 1 minute only.(Create)
  
# While we fetch them one by one we first collect them from the database and save them in both ehcache and redis cache. And we save the value in ehcache for 10 seconds only. After the first time of fetching the data if we fetch the same data again within 10 seconds it will deliver the data from ehcache, neither redis nor database. After the first time fetching request, the next one minute we store that value in the redis cache. So, for the next one minute it will deliver the particular value from redis.(Read)

# Update is work differently. It wont directly update the Database. The update happens in redis.

# Our scheduler responsibility is, in every 50 seconds it will take the redis cache value regarding the update. It will update all the values in database through this scheduler. 
