This project works as expected only when using the login method via the `QuarkusCookie`.

It also works with `OIDC` but only when the `@Transactional` is present at `TodoResource#todos`.

When `@Transactional` it's not present, the behaviour is unpredictable, because sometimes the CustomTenantResolver returns the `UUID` and sometimes the `base`.

Check the following two videos:

1. As you can see, I'm not logged in the Quarkus app although, I'm already logged in to my Google account, then I click to log in with my Google account, and I'm able to see the todos.
Then i comment the `@Transactional` and it stopped working.

https://litter.catbox.moe/t19xg2.mp4

2. In the second video, I remove the cookies, then press F5 to refresh the page which takes me to the login page, login again with my Google account, redirected to the /todos in which now I'm not able to see the todos (remember `@Transactional` is commented).
Now, I clear the cookies, I stop & start the Quarkus app, I click to refresh the page, login again with my Google account, and I'm able to see the todos (remember, `@Transaction` is commented).

https://litter.catbox.moe/c7q0hs.mp4

Also, I got another question, why does it add the tenant id twice? `t1_0.TID = ? and t1_0.TID = ?`