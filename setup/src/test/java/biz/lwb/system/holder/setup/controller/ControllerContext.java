package biz.lwb.system.holder.setup.controller;

import biz.lwb.system.holder.inmemory.service.SpringConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;


@Import(SpringConfig.class)
@ComponentScan("biz.lwb.system.holder")
public class ControllerContext {
}
