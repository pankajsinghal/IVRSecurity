package cn.bluejoe.elfinder.controller.executor;

import java.util.Map;

import com.bng.core.util.LogValues;
import com.bng.core.util.Logger;
import com.bng.core.util.coreException;

public class DefaultCommandExecutorFactory implements CommandExecutorFactory
{
	String _classNamePattern;

	private Map<String, CommandExecutor> _map;

	public CommandExecutor get(String commandName)
	{
		Logger.sysLog(LogValues.info, this.getClass().getName(), "command "+commandName);
		if (_map.containsKey(commandName))
			return _map.get(commandName);

		try
		{
			String className = String.format(_classNamePattern,
				commandName.substring(0, 1).toUpperCase() + commandName.substring(1));
			Logger.sysLog(LogValues.info, this.getClass().getName(), "command classname "+className);
			return (CommandExecutor) Class.forName(className).newInstance();
		}
		catch (Exception e)
		{
			Logger.sysLog(LogValues.error, this.getClass().getName(), coreException.GetStack(e));
			return null;
		}
	}

	public String getClassNamePattern()
	{
		return _classNamePattern;
	}

	public Map<String, CommandExecutor> getMap()
	{
		return _map;
	}

	public void setClassNamePattern(String classNamePattern)
	{
		_classNamePattern = classNamePattern;
	}

	public void setMap(Map<String, CommandExecutor> map)
	{
		_map = map;
	}
}
