## CommandActionService
ActionService for [Janet](https://github.com/techery/janet) which delegates job execution back to action.  

### Getting Started
##### 1. Define service and add it to `Janet`
```java
Janet janet = new Janet.Builder().addService(new CommandActionService()).build();
```

##### 2. Define command action class
```java
@CommandAction
class CopyBitmapToFileCommand extends CommandActionBase<File> {

    private final Context context;
    private final String bitmapPath;
    //
    private File newFile;

    public CopyBitmapToFileCommand(Context context, String bitmapPath) {
        this.context = context;
        this.bitmapPath = bitmapPath;
    }

    @Override protected void run(CommandCallback<File> callback) throws Throwable {
        // Pre-check arguments
        if (TextUtils.isEmpty(bitmapPath)) {
            callback.onFail(new IllegalArgumentException("Source path is empty"));
            return;
        }
        // Do the job
        String tmpBitmapPrefix = "bitmap_" + System.currentTimeMillis();
        try {
            newFile = BitmapUtil.decodeToTmp(context, bitmapPath, tmpBitmapPrefix);
            callback.onSuccess(newFile);
        } catch (IOException e) {
            callback.onFail(e);
        }
    }

    @Override protected void cancel() {
        // cleanup resources
        if (newFile != null && newFile.exists()) newFile.delete();
    }
}

```

* Each action is an individual class that contains runnable logic;
* It must be annotated with `@CommandAction` and must extend `CommandActionBase`;
* Real job is defined by overriden `void run()` method which is called upon action sending;
* `CommandCallback` methods should be called to indicate command result; 
* Cleanup on cancelation is available via overriden `void cancel()`.

##### 3. Use `ActionPipe` to send/observe action
```java
ActionPipe<CopyBitmapToFileCommand> actionPipe = janet.createPipe(CopyBitmapToFileCommand.class);
actionPipe
  .createObservable(new CopyBitmapToFileCommand(context, sourceBitmapPath))
  .subscribeOn(Schedulers.io())
  .subscribe(new ActionStateSubscriber<CopyBitmapToFileCommand>()
          .onSuccess(action -> System.out.println("Got new file " + action.getResult()))
          .onFail((action, throwable) -> System.err.println("Bad things happened " + throwable))
  );
```

### Download
```groovy
repositories {
    jcenter()
    maven { url "https://jitpack.io" }
}

dependencies {
    compile 'com.github.techery:janet-command:xxx'
    // explicitly depend on latest Janet for bug fixes and new features (optionally)
    compile 'com.github.techery:janet:zzz' 
}
```
* janet: [![](https://jitpack.io/v/techery/janet.svg)](https://jitpack.io/#techery/janet)
* janet-command: [![](https://jitpack.io/v/techery/janet-command.svg)](https://jitpack.io/#techery/janet-command)

## License

    Copyright (c) 2016 Techery

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

