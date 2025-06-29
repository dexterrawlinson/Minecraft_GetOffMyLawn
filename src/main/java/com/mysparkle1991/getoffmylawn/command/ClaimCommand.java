package com.mysparkle1991.getoffmylawn.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import com.mysparkle1991.getoffmylawn.procedures.*;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber
public class ClaimCommand {

    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        event.getDispatcher().register(
                Commands.literal("claim")
                        .executes(arguments -> {
                            Level world = arguments.getSource().getUnsidedLevel();
                            double x = arguments.getSource().getPosition().x();
                            double y = arguments.getSource().getPosition().y();
                            double z = arguments.getSource().getPosition().z();
                            Entity entity = arguments.getSource().getEntity();

                            if (entity == null && world instanceof ServerLevel) {
                                entity = FakePlayerFactory.getMinecraft((ServerLevel) world);
                            }

                            ClaimChunkProcedure.execute((LevelAccessor) world, x, y, z, entity);
                            return 0;
                        })
                        .then(Commands.literal("info")
                                .executes(arguments -> {
                                    Level world = arguments.getSource().getUnsidedLevel();
                                    double x = arguments.getSource().getPosition().x();
                                    double y = arguments.getSource().getPosition().y();
                                    double z = arguments.getSource().getPosition().z();
                                    Entity entity = arguments.getSource().getEntity();

                                    if (entity == null && world instanceof ServerLevel) {
                                        entity = FakePlayerFactory.getMinecraft((ServerLevel) world);
                                    }

                                    ClaimInfoProcedure.execute((LevelAccessor) world, x, y, z, entity);
                                    return 0;
                                })
                        )
                        .then(Commands.literal("admin")
                                .then(Commands.literal("add")
                                        .then(Commands.argument("name", EntityArgument.player())
                                                .executes(arguments -> {
                                                    Level world = arguments.getSource().getUnsidedLevel();
                                                    double x = arguments.getSource().getPosition().x();
                                                    double y = arguments.getSource().getPosition().y();
                                                    double z = arguments.getSource().getPosition().z();
                                                    Entity entity = arguments.getSource().getEntity();

                                                    if (entity == null && world instanceof ServerLevel) {
                                                        entity = FakePlayerFactory.getMinecraft((ServerLevel) world);
                                                    }

                                                    ClaimAddAdminProcedure.execute((LevelAccessor) world, x, y, z, arguments, entity);
                                                    return 0;
                                                })
                                        )
                                )
                                .then(Commands.literal("remove")
                                        .then(Commands.argument("name", EntityArgument.player())
                                                .executes(arguments -> {
                                                    Level world = arguments.getSource().getUnsidedLevel();
                                                    double x = arguments.getSource().getPosition().x();
                                                    double y = arguments.getSource().getPosition().y();
                                                    double z = arguments.getSource().getPosition().z();
                                                    Entity entity = arguments.getSource().getEntity();

                                                    if (entity == null && world instanceof ServerLevel) {
                                                        entity = FakePlayerFactory.getMinecraft((ServerLevel) world);
                                                    }

                                                    ClaimRemoveAdminProcedure.execute((LevelAccessor) world, x, y, z, arguments, entity);
                                                    return 0;
                                                })
                                        )
                                )
                                .then(Commands.literal("list")
                                        .executes(arguments -> {
                                            Level world = arguments.getSource().getUnsidedLevel();
                                            double x = arguments.getSource().getPosition().x();
                                            double y = arguments.getSource().getPosition().y();
                                            double z = arguments.getSource().getPosition().z();
                                            Entity entity = arguments.getSource().getEntity();

                                            if (entity == null && world instanceof ServerLevel) {
                                                entity = FakePlayerFactory.getMinecraft((ServerLevel) world);
                                            }

                                            ClaimAdminListProcedure.execute((LevelAccessor) world, x, y, z, entity);
                                            return 0;
                                        })
                                )
                        )
                        .then(Commands.literal("delete")
                                .executes(arguments -> {
                                    Level world = arguments.getSource().getUnsidedLevel();
                                    double x = arguments.getSource().getPosition().x();
                                    double y = arguments.getSource().getPosition().y();
                                    double z = arguments.getSource().getPosition().z();
                                    Entity entity = arguments.getSource().getEntity();

                                    if (entity == null && world instanceof ServerLevel) {
                                        entity = FakePlayerFactory.getMinecraft((ServerLevel) world);
                                    }

                                    ClaimDeleteProcedure.execute((LevelAccessor) world, x, y, z, entity);
                                    return 0;
                                })
                        )
                        .then(Commands.literal("transfer")
                                .then(Commands.argument("name", EntityArgument.player())
                                        .executes(arguments -> {
                                            Level world = arguments.getSource().getUnsidedLevel();
                                            double x = arguments.getSource().getPosition().x();
                                            double y = arguments.getSource().getPosition().y();
                                            double z = arguments.getSource().getPosition().z();
                                            Entity entity = arguments.getSource().getEntity();

                                            if (entity == null && world instanceof ServerLevel) {
                                                entity = FakePlayerFactory.getMinecraft((ServerLevel) world);
                                            }

                                            ClaimTransferProcedure.execute((LevelAccessor) world, x, y, z, arguments, entity);
                                            return 0;
                                        })
                                )
                        )
                        .then(Commands.literal("rename")
                                .then(Commands.argument("name", StringArgumentType.string())
                                        .executes(arguments -> {
                                            Level world = arguments.getSource().getUnsidedLevel();
                                            double x = arguments.getSource().getPosition().x();
                                            double y = arguments.getSource().getPosition().y();
                                            double z = arguments.getSource().getPosition().z();
                                            Entity entity = arguments.getSource().getEntity();

                                            if (entity == null && world instanceof ServerLevel) {
                                                entity = FakePlayerFactory.getMinecraft((ServerLevel) world);
                                            }

                                            ClaimRenameProcedure.execute((LevelAccessor) world, x, y, z, arguments, entity);
                                            return 0;
                                        })
                                )
                        )
                        .then(Commands.literal("setcategory")
                                .then(Commands.argument("name", StringArgumentType.string())
                                        .executes(arguments -> {
                                            Level world = arguments.getSource().getUnsidedLevel();
                                            double x = arguments.getSource().getPosition().x();
                                            double y = arguments.getSource().getPosition().y();
                                            double z = arguments.getSource().getPosition().z();
                                            Entity entity = arguments.getSource().getEntity();

                                            if (entity == null && world instanceof ServerLevel) {
                                                entity = FakePlayerFactory.getMinecraft((ServerLevel) world);
                                            }

                                            ClaimSetCategoryProcedure.execute((LevelAccessor) world, x, y, z, arguments, entity);
                                            return 0;
                                        })
                                )
                        )
                        .then(Commands.literal("reset")
                                .executes(arguments -> {
                                    Level world = arguments.getSource().getUnsidedLevel();
                                    double x = arguments.getSource().getPosition().x();
                                    double y = arguments.getSource().getPosition().y();
                                    double z = arguments.getSource().getPosition().z();
                                    Entity entity = arguments.getSource().getEntity();

                                    if (entity == null && world instanceof ServerLevel) {
                                        entity = FakePlayerFactory.getMinecraft((ServerLevel) world);
                                    }

                                    ClaimResetProcedure.execute((LevelAccessor) world, x, y, z, entity);
                                    return 0;
                                })
                        )
                        .then(Commands.literal("type")
                                .then(Commands.literal("public")
                                        .executes(arguments -> {
                                            Level world = arguments.getSource().getUnsidedLevel();
                                            double x = arguments.getSource().getPosition().x();
                                            double y = arguments.getSource().getPosition().y();
                                            double z = arguments.getSource().getPosition().z();
                                            Entity entity = arguments.getSource().getEntity();

                                            if (entity == null && world instanceof ServerLevel) {
                                                entity = FakePlayerFactory.getMinecraft((ServerLevel) world);
                                            }

                                            SetPublicProcedure.execute((LevelAccessor) world, x, y, z, entity);
                                            return 0;
                                        })
                                )
                                .then(Commands.literal("private")
                                        .executes(arguments -> {
                                            Level world = arguments.getSource().getUnsidedLevel();
                                            double x = arguments.getSource().getPosition().x();
                                            double y = arguments.getSource().getPosition().y();
                                            double z = arguments.getSource().getPosition().z();
                                            Entity entity = arguments.getSource().getEntity();

                                            if (entity == null && world instanceof ServerLevel) {
                                                entity = FakePlayerFactory.getMinecraft((ServerLevel) world);
                                            }

                                            SetPrivateProcedure.execute((LevelAccessor) world, x, y, z, entity);
                                            return 0;
                                        })
                                )
                        )
        );
    }
}