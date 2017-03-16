# NetworkLibrary

###### Sample Code

          `new Request(this, "http://example.com/test/sample.json", object, ExampleResponse.class, new IResponseCallback<ExampleResponse>() {
            @Override
            public void onResponse(ExampleResponse myResponse) {
                Log.d("TAG", myResponse.toString());
            }

            @Override
            public void onError(VolleyError volleyError) {

            }
        }).execute();`
        
## Public Methods:

##### Execute
> void execute();

##### Add headers
> Request addHeaders(Map<String, String>);

> Request addHeader(String, String);

##### Optional Methods:
> Request setShouldShowProgressDialog(boolean);

> Request setLoadingMessage(String);

> Request setLoadingMessage(int);
