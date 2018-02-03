# gdax-feed

Consume Gdax real-time feed using websocket.

### Step 1: 
create an account on https://www.gdax.com/ or use your coinbase credentials to login to gdax. 
Top right, hamburger icon, click on API, This will open a page which will help you generate
1. passphare
2. secret
3. api-key
4. set permissions for this access.

### Step 2:
java -jar command-line.jar \ 
      --gdax.api.phrase=<your-gdax-passphrase> \
      --gdax.api.secret=<your-gdax-secret> \
      --gdax.api.key=<your-gdax-key>
