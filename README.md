# wsc-sdk-java
https://travis-ci.org/garenwen/wsc-sdk-java


Example:
```java
    @Test
    public void execute() {
        //Url: "https://d-app.whispir.cc/a/app-027d30049900adf1/v1/events",
        //        AppId: "027d30049900adf1",
        //        AppSecret: "pULnwkHww9RuRFTcN9H3E9mAd0gcYBSmzAOqAYSEoBU=",
        WscAppClient wac = new DefaultWscClient("https://d-app.whispir.cc/a/app-027d30049900adf1/v1/events","027d30049900adf1","pULnwkHww9RuRFTcN9H3E9mAd0gcYBSmzAOqAYSEoBU=");

        WscRequest request = new WscRequest();
        Map<String,Object> event = new HashMap<String,Object>();

        event.put("to","garenwen@whispir.cc");
        event.put("content","hello world");

        request.setEvent(event);
        request.setName("foo");
        request.setSecure(false);
        try {
            WscResponse response = wac.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
```