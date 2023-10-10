/**
 *  并行任务控制
 */
class SuperTask {
    constructor(parallelCount = 2) {
        this.parnllelCount = parallelCount;
        this.tasks = [];
        this.runningCount = 0;
        // this.canRun = false;
    }

    addTask(task) {
        return new Promise((resolve, reject) => {
            this.tasks.push({task, resolve, reject});
            this._run();
        });
    }

    _run() {
        // this.canRun &&
        while (this.runningCount < this.parnllelCount && this.tasks.length > 0) {
            let {task, resolve, reject} = this.tasks.shift();
            this.runningCount++;
            task().then(resolve, reject).finally(() => {
                this.runningCount--;
                this._run();
            });
        }
    }

    // start() {
    //     this.canRun = true;
    //     this._run();
    // }
    // stop(){
    //     this.canRun = false;
    // }
}

export default SuperTask;

/**
 *
 *  test 任务控制
 */
// function timeout(time) {
//     return new Promise((resolve, reject) => {
//         setTimeout(() => resolve(), time)
//     })
// }
//
// const superTask = new SuperTask();
//
// function addTask(time, name) {
//     superTask.addTask(() => timeout(time)).then(() => console.log(`任务 ${name} 已完成！`))
// }
// await (async ()=>{
//     await addTask(1000, 'A');
//     await addTask(500, 'B');
//     await addTask(300, 'C');
//     await addTask(400, 'D');
//     await addTask(200, 'E');
// })()
//
// console.log("并行任务完成！")