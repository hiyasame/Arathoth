var Player = Packages.org.bukkit.entity.Player.class;

function onRegister(){
    ArathothII.getLogger().info("注册JavaScript属性: example");
}
function getType(){
    return 'ATTACK';
}
function setDefaultConfig(config) {
    config.set(getName()+".Settings.example","just a example attr");
    return config;
}
function getName() {
    return 'example';
}
function getDescription() {
    return 'JavaScript属性示例';
}
function onExecute(event,executor,data) {
    if (executor instanceof Player){
        executor.sendMessage("执行属性值: "+data.solveData());
    }
}